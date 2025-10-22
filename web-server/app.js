const express = require('express')
const app = express()
const port = 3000
const path = require('path')

// 페이지 라우팅
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/index.html'));
});

app.use((req, res) => {
  res.status(404).send('페이지를 찾을 수 없습니다.');
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})   