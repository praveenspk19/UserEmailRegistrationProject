import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/auth/register", form);
      alert("OTP sent to email");
      navigate("/verify", { state: { email: form.email } });
    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div className="container">
        <div className="card">
            <form onSubmit={handleSubmit}>
            <h2>Register</h2>
            <input name="username" placeholder="Username" onChange={handleChange} />
            <input name="email" placeholder="Email" onChange={handleChange} />
            <input type="password" name="password" placeholder="Password" onChange={handleChange} />
            <button type="submit">Register</button>
         </form>
        </div>
    </div>
    
  );
}

export default Register;
