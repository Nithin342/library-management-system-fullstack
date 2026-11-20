import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";
import RoleBased from "../components/RoleBased";
import "../styles/Members.css";


function Members() {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    getMembers();
  }, []);

  const getMembers = async () => {
    try {
      const response = await api.get("/members");

      setMembers(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load members.");
    } finally {
      setLoading(false);
    }
  };

  const deleteMember = async (id) => {
  const confirmDelete = window.confirm(
    "Are you sure you want to delete this member?"
  );

  if (!confirmDelete) {
    return;
  }

  try {
    await api.delete(`/members/${id}`);

    setMembers(
      members.filter((member) => member.id !== id)
    );
  } catch (error) {
  console.log(error);

  if (error.response && error.response.data) {
    const errors = error.response.data;

    if (typeof errors === "object") {
      const messages = Object.values(errors);
      setError(messages.join(" • "));
    } else {
      setError(errors);
    }
  } else {
    setError("Failed to delete member.");
  }
}
};

  if (loading) {
    return <h2>Loading members...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="members-page">

<div className="members-header">
  <h1>Members</h1>
</div>

 <div className="member-actions">

  <button
    className="back-dashboard-btn"
    onClick={() => navigate("/dashboard")}
  >
    ← Back to Dashboard
  </button>

  <RoleBased role="ADMIN">
    <Link to="/members/add" className="add-member-btn">
      + Add Member
    </Link>
  </RoleBased>

</div>

<p className="total-members">
  Total Members: {members.length}
</p>

      <div className="members-table-container">
  <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {members.map((member) => (
            <tr key={member.id}>
              <td>{member.id}</td>
              <td>{member.name}</td>
              <td>{member.email}</td>

              <td>
              <RoleBased role="ADMIN">
                <button className="edit-btn" onClick={() => navigate(`/members/edit/${member.id}`)} > Edit </button>
              </RoleBased>
               <RoleBased role="ADMIN">
                <button className="delete-btn" onClick={() => deleteMember(member.id)} > Delete </button>
              </RoleBased>

              <button className="view-borrowings-btn" onClick={() => navigate(`/members/${member.id}/borrowings`)} > View Borrowings </button>

              </td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
    </div>
  );
}

export default Members;