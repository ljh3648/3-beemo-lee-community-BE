const express = require('express')
const app = express()
const port = 3000

app.use(express.static('public'))

app.get('/about', (req, res) => {
  res.send('카카오테크 부트캠프 클라우드과정 커뮤니티 만들어보기 프로젝트입니다.')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
}) 