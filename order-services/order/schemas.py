from typing import List, Dict
from pydantic import BaseModel, Field
from enum import Enum


class CheckOutOrder(BaseModel):
    total_price: float
    fee_ship: float
    total_checkout_price: float

class CartProduct(BaseModel):
    product_id: str
    quantity: int
    price: float

class ShippingAddress(BaseModel):
    street: str
    ward: str
    district: str
    city: str

class CreateOrderRequest(BaseModel):
    user_id: str
    cart_id: str
    order_products: List[CartProduct]
    checkout_order: CheckOutOrder
    shipping_address: ShippingAddress

class ProductReserveResponse(BaseModel):
    productReservationStatus: Dict[str, bool]


# {
#   "cart_products": {
#     "cartId": "67ca5d6b59d99a3ec8e4fa11",
#     "cartProducts": [
#       {
#         "productId": "67c9afff880e4a7d4f0788a6",
#         "quantity": 8,
#         "productName": "Product 1",
#         "price": 79.99
#       },
#       {
#         "productId": "67c9b002880e4a7d4f0788a9",
#         "quantity": 2,
#         "productName": "Product 2",
#         "price": 79.99
#       }
#     ]
#   },
#   "checkout_order": {
#     "total_price": 799.9,
#     "fee_ship": 0,
#     "total_checkout_price": 799.9
#   }
# }