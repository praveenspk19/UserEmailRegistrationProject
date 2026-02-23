import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="container">
      <div className="card">
        <h1>Email Verification System</h1>
        <p>Please choose an option</p>

        <button onClick={() => navigate("/login")} className="btn">
          Login
        </button>

        <button onClick={() => navigate("/register")} className="btn secondary">
          Register
        </button>

        <button onClick={() => navigate("/SearchUsers")} className="btn secondary">
          Search Users
        </button>

        
      </div>
    </div>
  );
}

export default Home;
