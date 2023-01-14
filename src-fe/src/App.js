import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import LoginComponent from './components/LoginComponent';
import RegistrationComponent from './components/RegistrationComponent';

const App = () => {

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/registration" element={<RegistrationComponent />} />
          <Route path="/server-errror" element={<div>There some server errors ocured</div>} />
        </Routes>
      </BrowserRouter>
    </div>
  );

}

export default App