import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./pages/Register";
import VerifyOtp from "./pages/VerifyOtp";
import Login from "./pages/Login";
import Home from "./pages/Home";
import LoggedIn from "./pages/LoggedIn";
import "./app.css"
import SearchUsers from "./pages/SearchUsers";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/register" element={<Register />} />
        <Route path="/verify" element={<VerifyOtp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/loggedIn" element={<LoggedIn />}></Route>
         <Route path="/searchUsers" element={<SearchUsers />}></Route>
        
      </Routes>
    </BrowserRouter>
  );
}

export default App;
