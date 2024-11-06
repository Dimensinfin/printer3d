const EventSource = require('eventsource')

// URL of the SSE endpoint
const sseUrl = 'http://localhost:5120/sse/v1/events'
const secondUrl = 'http://localhost:8081/sse-server/stream-sse-mvc'
const serverNodeMock = 'http://localhost:3000/events'

// // Create an EventSource instance
// let eventSource = new EventSource(secondUrl)

// // Listen for messages from the server
// eventSource.onmessage = (event) => {
//   console.log('Received event:', event.data)
// }

// // Handle errors
// eventSource.onerror = (error) => {
//   console.error('Error occurred:', error)
//   // eventSource = new EventSource(sseUrl)
// }

// // Optionally, listen for specific event types
// eventSource.addEventListener('customEvent', (event) => {
//   console.log('Received custom event:', event.data)
// })

let retryInterval = 6000
let url =serverNodeMock

function listenToEvents(retryAfter) {
  let isListening = false

  const interval = setInterval(() => {
    if (!isListening) {
      isListening = true

      // const url = new URL(SSE_ENDPOINT, YOUR_API_BASE_URL);
      // const event = new EventSource(`${url.href}?id=${crypto.randomUUID()}`);
      console.log('Listening to emitter: ' + url)
      const event = new EventSource(url)

      event.onmessage = (e) => {
        console.log('Data received -> ' + e)
        const payload = JSON.parse(e.data)
        // do something with the payload
        payload.retry && (retryInterval = payload.retry)
      }

      event.onerror = (e) => {
        clearInterval(interval)
        console.log("Closend connection to emitter.")
        event.close()
        listenToEvents(retryInterval)
      }
    }
  }, retryAfter)
}

listenToEvents(1000) // initially, establish the connection in 1 second
