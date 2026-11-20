import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import "../styles/EditMember.css";

function EditMember() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    getMember();
  }, []);

  const getMember = async () => {
    try {
      const response = await api.get(`/members/${id}`);

      setName(response.data.name);
      setEmail(response.data.email);
    } catch (error) {
      console.log(error);
      setMessage("Failed to load member.");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const member = {
      name: name,
      email: email
    };

    try {
      await api.put(`/members/${id}`, member);

      setMessage("Member updated successfully!");

      setTimeout(() => {
        navigate("/members");
      }, 1000);
    } catch (error) {
      console.log(error);
      setMessage("Failed to update member.");
    }
  };

  return (
    <div className="edit-member-page">
  <div className="edit-member-container">

        <h1>Edit Member</h1>

        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label>Member Name</label>

            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Email</label>

            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <button type="submit">
            Update Member
          </button>

          {message && (
            <p className="form-message">{message}</p>
          )}

        </form>

      </div>
    </div>
  );
}

export default EditMember;