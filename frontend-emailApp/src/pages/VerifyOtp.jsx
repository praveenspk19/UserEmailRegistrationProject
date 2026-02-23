import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../api/axios";

function VerifyOtp() {
  const location = useLocation();
  const navigate = useNavigate();
  const email = location.state?.email;

  const [otp, setOtp] = useState("");
  const [error, setError] = useState("");
  const [showResend, setShowResend] = useState(false);

  const handleVerify = async () => {
    setError("");
    try {
      await api.post("/api/otp/verify", { email, otp });
      alert("Account verified");
      navigate("/login");
    } catch (err) {
      const message = err.response?.data || "Invalid OTP";
      setError(message);

      // Show resend button if expired or attempts exceeded
      if (
        message.toLowerCase().includes("expired") ||
        message.toLowerCase().includes("attempts")
      ) {
        setShowResend(true);
      }
    }
  };

  const handleResend = async () => {
    try {
      await api.post("/api/otp/resend-otp", { email });
      alert("New OTP sent");
      setShowResend(false);
      setOtp("");
      setError("");
    } catch (err) {
      alert( "Failed to resend OTP");
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h2>Verify OTP</h2>

        <input
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
        />

        <button onClick={handleVerify}>Verify</button>

        {error && <p style={{ color: "red" }}>{error}</p>}

        {showResend && (
          <button
            style={{ marginTop: "10px", backgroundColor: "#ff4d4d" }}
            onClick={handleResend}
          >
            Resend OTP
          </button>
        )}
      </div>
    </div>
  );
}

export default VerifyOtp;
