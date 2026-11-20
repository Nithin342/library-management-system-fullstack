import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/AddMember.css";

function AddMember() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const member = {
      name: name,
      email: email
    };

    try {
      await api.post("/members", member);

      setMessage("Member added successfully!");

      setTimeout(() => {
        navigate("/members");
      }, 1000);
   } catch (error) {
  console.log(error);

  if (error.response && error.response.data) {
    const errors = error.response.data;

    if (typeof errors === "object") {
      const messages = Object.values(errors);
      setMessage(messages.join(" • "));
    } else {
      setMessage(errors);
    }
  } else {
    setMessage("Failed to add member.");
  }
}
  };

  return (
    <div className="add-member-page">
  <div className="add-member-container">

        <h1>Add Member</h1>

        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label>Member Name</label>

            <input
              type="text"
              placeholder="Enter member name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Email</label>

            <input
              type="email"
              placeholder="Enter email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <button type="submit">
            Add Member
          </button>

          {message && (
            <p className="form-message">{message}</p>
          )}

        </form>

      </div>
    </div>
  );
}

export default AddMember;