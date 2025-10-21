const express = require('express')
const app = express()
const port = 3000
const path = require('path')

// 페이지 라우팅
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/index.html'));
});

app.get('/signin', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/signin/index.html'));
});

app.get('/signup', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/signup/index.html'));
});

app.get('/home', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/home/index.html'));
});

app.get('/posts/create', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/posts/create/index.html'));
});

app.get('/posts/:id', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/posts/detail/index.html'));
});

app.get('/posts/:id/edit', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/pages/posts/edit/index.html'));
});

app.use(express.static('public/'))
app.use(express.static('public/pages/'))

app.use((req, res) => {
  res.status(404).send('페이지를 찾을 수 없습니다.');
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})   