from typing import Optional, List, Dict, Any
from sqlmodel import SQLModel, Field, Relationship, Column, JSON
from datetime import datetime
import sqlalchemy.dialects.postgresql as pg
from order.constants import OrderStatus


# class ShippingAddress(SQLModel, table = True):
#     __tablename__ = "shipping_addresses"
#
#     id: Optional[int] = Field(default=None, primary_key=True)
#     street: str
#     ward: str
#     district: str
#     city: str
#
#     order: Optional["Order"] = Relationship(back_populates="shipping_address")

# class OrderProducts(SQLModel, table=True):
#     __tablename__ = "order_products"
#
#     id: Optional[int] = Field(default=None, primary_key=True)
#     product_id: str
#     quantity: int
#     price: float
#
#     order_id: Optional[int] = Field(default=None, foreign_key="orders.id")
#     order: Optional["Order"] = Relationship(back_populates="order_products")

# class CheckOutOrder(SQLModel, table=True):
#     __tablename__ = "checkout_orders"
#
#     id: Optional[int] = Field(default=None, primary_key=True)
#     total_price: float
#     fee_ship: float
#     total_checkout_price: float
#
#     order_id: Optional[int] = Field(default=None, foreign_key="orders.id")
#     order: Optional["Order"] = Relationship(back_populates="checkout_order")


class Order(SQLModel, table = True):
    __tablename__ = "orders"

    id: Optional[int] = Field(default=None, primary_key=True, index=True)
    user_id: Optional[str]
    order_products: List[Dict[str, Any]] = Field(
        default_factory= lambda : [{
            "product_id": "",
            "quantity": 0,
            "price": 0.0 }
        ]
        ,sa_column=Column(JSON)
    )
    order_tracking_number: Optional[str] = Field(sa_column=Column(pg.VARCHAR, nullable=True))
    checkout_order: Dict[str, Any] = Field(
        default_factory= lambda :{
            "total_price": 0.0,
            "fee_ship": 0.0,
            "total_checkout_price": 0.0
        }, sa_column=Column(JSON)
    )
    status: OrderStatus = Field(
        sa_column=Column(pg.ENUM(OrderStatus, name="order_status_enums"), nullable=False, default=OrderStatus.PENDING)
    )

    # shipping_address_id: Optional[int] = Field(default=None, foreign_key="shipping_addresses.id")
    shipping_address: Dict[str, Any] = Field(
        default_factory= lambda :{
            "street": "",
            "ward": "",
            "district": "",
            "city": ""
        }, sa_column=Column(JSON)
    )

    created_at: datetime = Field(sa_column=Column(pg.TIMESTAMP(timezone=True), default=datetime.now))
    updated_at: datetime = Field(sa_column=Column(pg.TIMESTAMP(timezone=True), default=datetime.now, onupdate=datetime.now))



