from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    PRODUCT_SERVICES_URL: str = "http://localhost:8082/api/v1"
    DATABASE_URL: str

    model_config = SettingsConfigDict(env_file=".env", extra="ignore")

Config = Settings()