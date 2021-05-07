This application requires following environment variables:
DB_URL - URL to postgres database
DB_LOGIN - Database user login
DB_PASSWORD - Database user password
JWT_KEY - Random 256-bit key for signing JWT-tokens in base64
ADMINS - Comma-separated list of administrators' logins (optional)
DEFAULT_ADMIN_LOGIN - Login of admin to be created if no other active employee was found
DEFAULT_ADMIN_PASSWORD - Password of default admin
SMS_RU_API_ID - API key used to send SMS using SMS.RU service
