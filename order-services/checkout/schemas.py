from pydantic import BaseModel, Field
from typing import List

class CartProductRequest(BaseModel):
    product_id: str
    quantity: int


class CheckOutRequest(BaseModel):
    user_id: str
    cart_id: str
    cart_products: List[CartProductRequest]

    class Config:
        from_attributes = True

class CartProductResponse(BaseModel):
    product_id: str = Field(..., alias="productId")
    quantity: int
    product_name: str = Field(..., alias="productName")
    price: float

class ValidateForCheckOutResponse(BaseModel):
    cart_id: str = Field(..., alias="cartId")
    cart_products: List[CartProductResponse] = Field(..., alias="cartProducts")

class CheckOutOrder(BaseModel):
    total_price: float
    fee_ship: float
    total_checkout_price: float

class CheckOutResponse(BaseModel):
    user_id: str
    cart_id: str
    cart_products: List[CartProductResponse]
    checkout_order: CheckOutOrder
