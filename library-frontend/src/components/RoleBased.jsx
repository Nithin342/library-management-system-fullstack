function RoleBased({ role, children }) {
  const userRole = localStorage.getItem("role");

  if (userRole !== role) {
    return null;
  }

  return children;
}

export default RoleBased;