package ru.vsu.cs.carsharing.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.carsharing.dto.FileInfoDto;
import ru.vsu.cs.carsharing.exception.FileStorageException;
import ru.vsu.cs.carsharing.exception.StorageException;
import ru.vsu.cs.carsharing.exception.StorageFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class StorageService {
    public String uploadDir = "/uploads";
    private final Path rootLocation = Paths.get(uploadDir);


    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


//    public Resource loadAsResourceDemand(Integer demandId, String folder, String filename) {
//        try {
//            final String fullFilePath = String.join(File.separator, demandRootPath, demandId.toString(), folder, filename);
//
//            final Path file = load(fullFilePath);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                log.error("File not found : {}", fullFilePath);
//                throw new StorageFileNotFoundException(
//                        "Could not read file: " + fullFilePath);
//            }
//        } catch (MalformedURLException e) {
//            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
//        }
//    }


    public Resource loadAsResourceImport(String folder, String filename) {
        try {
            final String fullFilePath = String.join(File.separator, uploadDir, "car", folder, filename);

            final Path file = load(fullFilePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("File not found : {}", fullFilePath);
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fullFilePath);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


    public void removeFileForCar(String folder, String filename) {
        try {
            final String fullFilePath = String.join(File.separator, uploadDir, "car", folder, filename);

            final Path file = load(fullFilePath);
            if (!Files.deleteIfExists(file)) {
                log.error("File not found : {}", fullFilePath);
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fullFilePath);
            }
        } catch (IOException ioException) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, ioException);
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    /**
     *
     * @param file
     * @param subfolderPath
     */
    public void uploadFileToSubfolder(MultipartFile file, String subfolderPath) {
        try {
            final String fullFilePath = String.join(File.separator, uploadDir, subfolderPath, StringUtils.cleanPath(file.getOriginalFilename()));
            Path copyLocation = Paths.get(fullFilePath);
            Path directory = copyLocation.getParent();

            if (Files.notExists(directory)) {
                try {
                    Files.createDirectories(directory);
                } catch (IOException e) {
                    log.error("Error while creating directory", e);
                }
            }

            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            log.error("Error while uploading file", e);
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    public String getUploadDir() {
        return this.uploadDir;
    }

    /**
     *
     * return list of files from directory
     *
     * @param directory
     * @return
     */
    public List<FileInfoDto> getFileInfoFromDirectory(String directory) {
        Path path = Paths.get(directory);
        if (Files.notExists(path)) {
            return Collections.emptyList();
        }

        try (Stream<Path> paths = Files.list(path)) {
            return paths
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(this::getFileLastModifiedTimeIgnoringIOException).reversed())
                    .map(this::pathToDto)
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            log.error("Something bad happened", ioException);
            return Collections.emptyList();
        }
    }

    private long getFileLastModifiedTimeIgnoringIOException(Path path) {
        try {
            return Files.getLastModifiedTime(path).toMillis();
        } catch (IOException e) {
            return 0;
        }
    }

    private FileInfoDto pathToDto(Path path) {
        String fileName = path.getFileName().toString();
        return new FileInfoDto(fileName, null, path.toString());

    }
}
