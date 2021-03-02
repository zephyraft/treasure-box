dependencies {
    val jedisVersion: String by project
    val redissonVersion: String by project

    implementation("redis.clients", "jedis", jedisVersion)
    implementation("org.redisson", "redisson", redissonVersion)
    implementation("com.fasterxml.jackson.core", "jackson-databind")
}