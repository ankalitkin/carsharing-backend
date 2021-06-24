package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vsu.cs.carsharing.dto.FileInfoDto;
import ru.vsu.cs.carsharing.exception.StorageFileNotFoundException;
import ru.vsu.cs.carsharing.security.CurrentUserService;
import ru.vsu.cs.carsharing.service.StorageService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {

    private static final String uploadDirectory = "/uploads";
    private final StorageService storageService;

    /**
     *
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        log.debug("Storage temp directory is set to {}", uploadDirectory);

        Path dataDir = Paths.get(uploadDirectory);
        Files.createDirectories(dataDir);
    }

    @GetMapping("/fileUpload/car/{folder}")
    public List<FileInfoDto> getAllCarFilesFromFolder(@PathVariable String folder) {
        String directory = storageService.getUploadDir() + File.separator + "car" + File.separator + folder;
        log.debug("File upload full path {}", directory);
        return storageService.getFileInfoFromDirectory(directory);
    }

    @GetMapping("/fileUpload/customer")
    public ResponseEntity<List<FileInfoDto>> getAllCustomerFilesFromFolder() {
        String directory = storageService.getUploadDir() + File.separator + "customer" + File.separator + CurrentUserService.getCustomerId();
        log.debug("File upload full path {}", directory);

        List<FileInfoDto> fileInfoDtos = storageService.getFileInfoFromDirectory(directory);

        return new ResponseEntity<>(fileInfoDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Employee')")
    @PostMapping("/fileUpload/car/{folder}")
    public ResponseEntity<Void> uploadFileForCar(@RequestParam("file") MultipartFile file,
                                                 @PathVariable String folder, RedirectAttributes redirectAttributes) {

        storageService.uploadFileToSubfolder(file, "car" + File.separator + folder);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Customer')")
    @PostMapping("/fileUpload/customer")
    public ResponseEntity<Void> uploadFileForCustomer(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        storageService.uploadFileToSubfolder(file, "customer" + File.separator + CurrentUserService.getCustomerId());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/downloadFile/car/{folder}/{filename:.+}")
    public ResponseEntity<Resource> downloadCarFile(@PathVariable String filename,
                                                    @PathVariable String folder) {

        try {
            Resource file = storageService.loadAsResourceImport(folder, filename);
            String mimeTypeStr;
            try {
                mimeTypeStr = Files.probeContentType(Paths.get(file.getFilename()).getFileName());
                log.debug("Current mimetype {}", mimeTypeStr);
            } catch (InvalidMediaTypeException imtex) {
                log.error("Error while detecting media type", imtex);
            }
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (StorageFileNotFoundException | IOException ex) {
            log.error("Error while reading file", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/fileUpload/car/{folder}/{filename:.+}")
    public ResponseEntity<Void> removeDemandFile(@PathVariable String filename, @PathVariable String folder) {
        try {
            storageService.removeFileForCar(folder, filename);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StorageFileNotFoundException ex) {
            log.error("Error while reading file", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
