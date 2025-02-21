from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from uuid import UUID

from models import Profile, Address


class ProfileRepository:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def save(self, profile: Profile):
        self.session.add(profile)
        await self.session.commit()
        await self.session.refresh(profile)
        return profile

    async def find_by_id(self, profile_id: int):
        result = await  self.session.execute(
            select(Profile).filter_by(id=profile_id)
        )
        return result.scalars().first()

    async def find_by_user_id(self, user_id: UUID):
        result = await self.session.execute(
            select(Profile).filter_by(user_id=user_id)
        )
        return result.scalars().first()

class AddressRepository:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def save(self, address: Address) -> Address:
        self.session.add(address)
        await self.session.commit()
        await self.session.refresh(address)
        return address

    async def find_by_id(self, address_id: UUID):
        result = await self.session.execute(
            select(Address).filter_by(id=address_id)
        )
        return result.scalars().first()