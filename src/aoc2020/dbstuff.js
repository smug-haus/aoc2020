// DB helper

// A function that creates pools



// A function that delivers a query

async function query(connection, query) {
  
  try {
      const result = await connection.query(query)
  } catch (e) {
    throw e
  }

  return {"result": result, "connection"}

}

const compose = (...fs) => (x) => fs.reduceRight((acc, f) => f(acc), x)

function connect(setting, creds) {
  let connection = createPool(settings, creds).getConnection()
  
  async function queryFn(queryString) {
    try {
      return connection.query(queryString)
    } catch (e) {
      throw e
    }
  }

  async function disconnectFn(){
    return connection.disconnect()
  }

  return {
    query: queryFn,
    disconnect: disconnectFn
  }

}

db = connect({},{})

await db.query(myquery)
await db.query(things)