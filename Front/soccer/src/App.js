import { Routes, Route } from "react-router-dom";

import MainPage from "./pages/MainPage/MainPage";
import Layout from "./components/Layout";
function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<MainPage />} />
      </Routes>
    </Layout>
  );
}

export default App;
