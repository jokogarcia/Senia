package RandomString
fun generar(charPool:List<Char>,len:Int):String  = (1..len)
    .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")