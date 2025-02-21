from uuid import UUID

from pydantic import BaseModel, Field
from enum import Enum

class City(str, Enum):
    HANOI = "Hanoi"
    HOCHIMINH = "Ho Chi Minh"
    DANANG = "Da Nang"


class AddressRequest(BaseModel):
    street: str = Field(..., min_length=3, max_length=100, description="Street name and house number")
    ward: str = Field(..., min_length=2, max_length=50, description="Ward or commune (Phường/Xã)")
    district: str = Field(..., min_length=2, max_length=50, description="District (Quận/Huyện)")
    city: str = Field(..., min_length=2, max_length=50, description="City or Province (Thành phố/Tỉnh)")

    class Config:
        from_attributes = True

class AddressResponse(BaseModel):
    street: str
    ward: str
    district: str
    city: City

    class Config:
        from_attributes = True

class ProfileCreationRequest(BaseModel):
    user_id: UUID
    full_name: str = Field(..., min_length=2, max_length=50)
    phone: str # No validation yet
    address: AddressRequest

    class Config:
        from_attributes = True

class ProfileCreationResponse(BaseModel):
    id: UUID
    user_id: UUID
    full_name: str
    phone: str
    address_id: UUID

    class Config:
        from_attributes = True

class ProfileResponse(BaseModel):
    id: UUID
    user_id: UUID
    full_name: str
    phone: str
    address: AddressResponse

    class Config:
        from_attributes = True