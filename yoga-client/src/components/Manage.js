import { useState, useEffect } from "react";
import { searchUsers,saveUser } from '../services/userService'; // Import your search function
import { Modal, Button } from 'react-bootstrap'; 
import { useNavigate } from "react-router-dom";
import UserSearch from "./UserSearch";



function ManageUsers() {

  const navigate = useNavigate();


  const handleRowDoubleClick = (user) => {
    console.log(user.id)
    navigate(`/profile/${user.appUserId}`, { state: { user } });
  };


  return (
    <div className="manage-userss">
      <h1>Manage Users</h1>
      <UserSearch onUserSelect={handleRowDoubleClick}/>
    </div>
  );
}

export default ManageUsers;
