import json

from aiokafka import AIOKafkaProducer

from config import Config


class KafkaProducer:
    def __init__(self, servers: str):
        self.producer = AIOKafkaProducer(bootstrap_servers=servers)

    async def start(self):
        await self.producer.start()

    async def send(self, topic: str, data: dict):
        await self.producer.send(topic, json.dumps(data).encode('utf-8'))

    async def stop(self):
        await self.producer.stop()

kafka_producer = KafkaProducer(servers=Config.KAFKA_BOOTSTRAP_SERVER)