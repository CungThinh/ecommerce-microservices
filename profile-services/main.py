from fastapi import FastAPI
from routes import profile_router
from exceptions import register_all_errors

version = "v1"
version_prefix =f"/api/{version}"
app = FastAPI(
    title="Profile Services",
    docs_url=f"{version_prefix}/docs"
)

register_all_errors(app)

app.include_router(profile_router, prefix=f"{version_prefix}/profiles", tags=["profiles"])