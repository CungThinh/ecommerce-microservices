from contextlib import asynccontextmanager

from fastapi import FastAPI
from checkout.routes import checkout_router
from config import Config
from messaging.kafka_producer import KafkaProducer
from order.routes import order_router
from order.hooks import register_event_listeners
from exceptions import register_all_errors
from messaging.kafka_producer import kafka_producer

version = "v1"
version_prefix =f"/api/{version}"

@asynccontextmanager
async def lifespan(app: FastAPI):
    await kafka_producer.start()
    yield  # This point is where your application runs
    await kafka_producer.stop()
app = FastAPI(
    title="Order Services",
    docs_url=f"{version_prefix}/docs",
    lifespan=lifespan  # Add the lifespan manager here
)

register_event_listeners()
register_all_errors(app)

app.include_router(checkout_router, prefix=f"{version_prefix}/checkout", tags=["checkout"])
app.include_router(order_router, prefix=f"{version_prefix}/order", tags=["order"])


