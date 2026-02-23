import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post("api/auth/login", form);
    
        alert("Login successful");
      //  console.log(res.data);
        navigate("/loggedIn");
     
    } catch (err) {
      alert(err.response?.data || "Login failed");
    }
  };

  return (
    <div className="container">
        <div className="card">
            <form onSubmit={handleLogin}>
                <h2>Login</h2>
                <input name="email" placeholder="Email" onChange={handleChange} />
                <input type="password" name="password" placeholder="Password" onChange={handleChange} />
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
    
  );
}

export default Login;
