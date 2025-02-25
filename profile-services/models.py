import datetime

from sqlalchemy import Integer, Column, String, DateTime, ForeignKey, Enum
from sqlalchemy.dialects.postgresql import UUID
import uuid

from sqlalchemy.orm import relationship

from database import Base
from schemas import City

class Address(Base):
    __tablename__ = "addresses"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, index=True)
    street = Column(String(100), nullable=False)
    ward = Column(String(50), nullable=False)
    district = Column(String(50), nullable=False)
    city = Column(Enum(City), nullable=False)

    profile = relationship("Profile", back_populates="address")

class Profile(Base):
    __tablename__ = "profiles"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    user_id = Column(UUID(as_uuid=True), index=True, nullable=True)
    full_name = Column(String(100), nullable=False)
    phone = Column(String(15), nullable=False)
    address_id = Column(UUID(as_uuid=True), ForeignKey("addresses.id"), nullable=False)

    address = relationship("Address", back_populates="profile", lazy="selectin")