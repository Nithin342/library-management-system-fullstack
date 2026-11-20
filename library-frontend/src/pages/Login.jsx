import { useState } from "react";
import axios from "axios";
import { API_BASE_URL } from "../services/api";
import "../styles/Login.css";
import { jwtDecode } from "jwt-decode";
import { Navigate, useNavigate } from "react-router-dom";

function Login() {
  const token = localStorage.getItem("token");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  if (token) {
    return <Navigate to="/dashboard" replace />;
  }

 const handleLogin = async (e) => {
  e.preventDefault();

if (!username.trim() || !password.trim()) {
  setMessage("Username and password are required.");
  return;
}

setLoading(true);

  try {
    const response = await axios.post(
      `${API_BASE_URL}/login`,
      {
        username: username,
        password: password,
      }
    );

   const token = response.data;

localStorage.setItem("token", token);

const decodedToken = jwtDecode(token);

localStorage.setItem("role", decodedToken.role);

setMessage("Login successful!");

navigate("/dashboard");


 } catch (error) {
  console.log(error);

  if (error.response && error.response.status === 401) {
    setMessage("Invalid username or password.");
  } else {
    setMessage("Something went wrong. Please try again.");
  }
}
finally {
  setLoading(false);
}
 }

 return (
  <div className="login-page">

    <div className="login-container">

      <h1>Library Management System</h1>

      <h2>Login</h2>

      <form onSubmit={handleLogin}>

        <div className="form-group">
          <label htmlFor="username">Username</label>

          <input
            id="username"
            type="text"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>

          <input
            id="password"
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
        </button>
        {message && <p>{message}</p>}

      </form>

    </div>

  </div>
);
}

export default Login;