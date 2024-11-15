# Springboot-mongodb-redis
This application contains spring boot, mongodb, redis cache.


# Installing Redis on Windows

### Install Redis Using Windows Subsystem for Linux (WSL)

**Enable WSL** (if not already enabled):
Open PowerShell as Administrator and run the following command:

> wsl --install

### Install Redis
> curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg

> echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list

> sudo apt-get update

> sudo apt-get install redis

### Start the Redis server

> sudo service redis-server start

### Connect to Redis

> redis-cli

You should see the Redis CLI prompt:
127.0.0.1:6379>

### ping the redis server

> 127.0.0.1:6379>ping

You should receive the following response:
PONG


# Redis Basic Commands

### Set a key-value pair
Sets a key with a value
> SET name "Rajesh"  
*Example:* This sets the key `name` with the value `Rajesh`.

### Get the value of a key
Retrieves the value of a key
> GET name  
*Example:* This retrieves the value of the key `name`, which will return `Rajesh`.

### Delete a key
Deletes a key
> DEL name  
*Example:* This deletes the key `name` from Redis.

### Check if a key exists
Checks if a key exists
> EXISTS name  
*Example:* This checks if the key `name` exists. It will return `1` if the key exists and `0` if it doesn't.

### Set a key with an expiration (TTL)
Sets a key with an expiration time (in seconds)
> SETEX session 300 "Rajesh"  
*Example:* This sets the key `session` with the value `Rajesh` that expires in 300 seconds.

### Get the time-to-live (TTL) of a key
Retrieves the TTL (time to live) of a key
> TTL session  
*Example:* This retrieves the TTL of the key `session`, which will return the number of seconds until it expires.

### Add elements to a list
Adds an element to the head of a list
> LPUSH friends "Rajesh"  
*Example:* This adds the value `Rajesh` to the head of the list `friends`.

### Retrieve all elements from a list
Retrieves a range of elements from a list
> LRANGE friends 0 -1  
*Example:* This retrieves all elements from the list `friends`, including `Rajesh`.

### Set a field in a hash
Sets a field in a hash
> HSET user:1000 name "Rajesh"  
*Example:* This sets the field `name` in the hash `user:1000` to `Rajesh`.

### Get a field from a hash
Retrieves the value of a field in a hash
> HGET user:1000 name  
*Example:* This retrieves the value of the `name` field from the hash `user:1000`, which will return `Rajesh`.

### Get all fields and values in a hash
Retrieves all fields and values of a hash
> HGETALL user:1000  
*Example:* This retrieves all fields and values of the hash `user:1000`, including `name: Rajesh`.

### Get all keys
Retrieves all keys in the current database
> KEYS *  
*Example:* This retrieves all keys in the current database. It may return keys like `name`, `session`, `friends`, etc.

### Delete all keys
Deletes all keys in all databases (use with caution)
> FLUSHALL  
*Example:* This deletes all keys in all Redis databases, including the `name`, `session`, and `friends` keys. 