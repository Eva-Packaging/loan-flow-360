import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

import { Routes, Route } from 'react-router-dom'
import Home from './Pages/Home'

function App() {

  console.log(import.meta.env.VITE_API_URL);
  return (
    <>
    <Routes>
      <Route path="/" element={<Home />} />
    </Routes>
    </>
  )
}

export default App
