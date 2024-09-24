import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function ManageInstructors() {
  const [instructors, setInstructors] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  // Fetch instructors from an API or some data source
  useEffect(() => {
    // Fetch the instructor data
    // Replace with your API call
    fetchInstructors();
  }, []);

  const fetchInstructors = async () => {
    // Replace with your actual fetch call
    const data = await fetch("/api/instructors");
    const result = await data.json();
    setInstructors(result);
  };

  // Handle search input change
  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setCurrentPage(1); // Reset to the first page on a new search
  };

  // Filter and paginate instructors
  const filteredInstructors = instructors.filter((instructor) =>
    instructor.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const indexOfLastInstructor = currentPage * itemsPerPage;
  const indexOfFirstInstructor = indexOfLastInstructor - itemsPerPage;
  const currentInstructors = filteredInstructors.slice(
    indexOfFirstInstructor,
    indexOfLastInstructor
  );

  const totalPages = Math.ceil(filteredInstructors.length / itemsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleEdit = (instructorId) => {
    // Handle edit logic here
    console.log("Edit instructor with id:", instructorId);
  };

  const handleDelete = (instructorId) => {
    // Handle delete logic here
    console.log("Delete instructor with id:", instructorId);
  };

  return (
    <div className="manage-instructors">
      <h1>Manage Instructors</h1>

      {/* Search Bar */}
      <input
        type="text"
        placeholder="Search instructors..."
        value={searchQuery}
        onChange={handleSearchChange}
        className="form-control mb-4"
      />

      {/* Instructor List */}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {currentInstructors.map((instructor) => (
            <tr key={instructor.id}>
              <td>{instructor.name}</td>
              <td>{instructor.email}</td>
              <td>
                <button
                  className="btn btn-primary"
                  onClick={() => handleEdit(instructor.id)}
                >
                  Edit
                </button>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(instructor.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Pagination */}
      <div className="pagination">
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i + 1}
            onClick={() => handlePageChange(i + 1)}
            className={`btn ${currentPage === i + 1 ? "btn-primary" : "btn-secondary"}`}
          >
            {i + 1}
          </button>
        ))}
      </div>

      {/* Add New Instructor Button */}
      <div className="mt-4">
        <Link to="/add-instructor" className="btn btn-success">
          Add New Instructor
        </Link>
      </div>
    </div>
  );
}

export default ManageInstructors;
