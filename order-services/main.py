from fastapi import FastAPI
from checkout.routes import checkout_router
from order.routes import order_router
from order.hooks import register_event_listeners
from exceptions import register_all_errors

version = "v1"
version_prefix =f"/api/{version}"
app = FastAPI(
    title="Order Services",
    docs_url=f"{version_prefix}/docs"
)

register_event_listeners()
register_all_errors(app)

app.include_router(checkout_router, prefix=f"{version_prefix}/checkout", tags=["checkout"])
app.include_router(order_router, prefix=f"{version_prefix}/order", tags=["order"])



