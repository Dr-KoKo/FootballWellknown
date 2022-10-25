import React from 'react';
import Home from './Home';
import MatchMain from './pages/MatchPage/MatchMain';
import Layout from './components/Layout';
import { Route, Routes } from 'react-router';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/match" element={<MatchMain/>}/>
      </Routes>
    </Layout>
  );
}

export default App;