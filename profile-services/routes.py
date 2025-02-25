from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.ext.asyncio import AsyncSession
from starlette import status
import logging
from database import get_db
from exceptions import ProfileAlreadyExists
from jwt import get_user_scope, RoleChecker
from schemas import ProfileCreationResponse, ProfileCreationRequest, ProfileResponse
from service import ProfileService
from uuid import UUID

profile_router = APIRouter()
admin_role_checker = RoleChecker(["ROLE_ADMIN"])
user_role_checker = RoleChecker(["ROLE_USER"])

def get_profile_service(db: AsyncSession = Depends(get_db)) -> ProfileService:
    return ProfileService(db)

@profile_router.post("", response_model=ProfileCreationResponse, status_code=status.HTTP_201_CREATED)
async def add_profile(profile_data: ProfileCreationRequest, service: ProfileService = Depends(get_profile_service)):
    existing_profile = await service.get_profile_by_user_id(profile_data.user_id)
    if existing_profile:
        raise ProfileAlreadyExists()
    new_profile = await service.create_profile(profile_data)
    return new_profile

@profile_router.get("/{user_id}", response_model=ProfileResponse, status_code=status.HTTP_200_OK)
async def get_profile_by_user_id(user_id: UUID, service: ProfileService = Depends(get_profile_service)):
    profile = await service.get_profile_by_user_id(user_id)
    return profile

@profile_router.get("", response_model=list[ProfileResponse], status_code=status.HTTP_200_OK, dependencies=[Depends(admin_role_checker)])
async def get_all_profiles(service: ProfileService = Depends(get_profile_service)):
    profiles = await service.get_all_profiles()
    return profiles