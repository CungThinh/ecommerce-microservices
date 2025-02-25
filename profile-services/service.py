from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.orm import Session

from models import Address, Profile
from repository import ProfileRepository, AddressRepository
from schemas import ProfileCreationRequest
from uuid import UUID

class ProfileService:
    def __init__(self, session: AsyncSession):
        self.session = session
        self.profile_repo = ProfileRepository(session)
        self.address_repo = AddressRepository(session)

    async def create_profile(self, profile_request: ProfileCreationRequest) -> Profile:
        new_address = Address(
            street=profile_request.address.street,
            ward=profile_request.address.ward,
            district=profile_request.address.district,
            city=profile_request.address.city
        )

        await self.address_repo.save(new_address)

        new_profile = Profile(
            user_id=profile_request.user_id,
            full_name=profile_request.full_name,
            phone=profile_request.phone,
            address_id=new_address.id
        )

        return await self.profile_repo.save(new_profile)

    async def get_profile_by_user_id(self, user_id: UUID) -> Profile:
        return await self.profile_repo.find_by_user_id(user_id)

    async def get_all_profiles(self):
        return await self.profile_repo.get_all()