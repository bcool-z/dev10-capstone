import { useState } from "react";
import { Link } from "react-router-dom";
import { register } from "../services/authService";

import ValidationSummary from "./ValidationSummary";

function SignUpForm() {
  const [errors, setErrors] = useState([]);
  const [credentials, setCredentials] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    dob: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [success, setSuccess] = useState(false);

  const handleChange = (evt) => {
    const nextCredentials = { ...credentials };
    nextCredentials[evt.target.name] = evt.target.value;
    setCredentials(nextCredentials);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    setErrors([]);

    const validationErrors = validateForm;
    if (validationErrors.length>0) {
      setErrors(validationErrors);
      return;
    }

    register(credentials).then((data) => {
      if (data && data.errors) {
        setErrors(data.errors);
      } else {
        setSuccess(true);
      }
    });
  };

  const validateForm = () => {
    const errors = [];
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^[0-9]{10}$/;

    if (emailRegex.test(credentials.username)){
      errors.push("must be valid email.")
    }

    if (!/[^a-zA-Z0-9\s]/.test(credentials.password)) {
      errors.push("Password must contain at least one special character.");
    }
    // Password must be at least 8 characters long
    if (credentials.password.length < 8) {
      errors.push("Password must be at least 8 characters long.");
    }
  
    // Password must contain at least one number
    if (!/\d/.test(credentials.password)) {
      errors.push("Password must contain at least one number.");
    }
  
    // Password must contain at least one uppercase letter
    if (!/[A-Z]/.test(credentials.password)) {
      errors.push("Password must contain at least one uppercase letter.");
    }
  
    // Password must contain at least one lowercase letter
    if (!/[a-z]/.test(credentials.password)) {
      errors.push("Password must contain at least one lowercase letter.");
    }
  
    // Passwords must match
    if (credentials.password !== credentials.confirmPassword) {
      errors.push("Passwords do not match.");
    }
  
    return errors;
  };

  return (
    <div>
      <ValidationSummary errors={errors} />
      {success ? (
        <div className="alert alert-success">
          Congratulations {credentials.username}, you have been registered.
          Login <Link to="/login">here</Link>.
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <div>
            
            <div className="form-group">
              <label htmlFor="firstName">First Name</label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                name="firstName"
                value={credentials.firstName}
                onChange={handleChange}
                required
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="lastName">Last Name</label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                name="lastName"
                value={credentials.lastName}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="phoneNumber">Phone Number</label>
              <input
                type="tel"
                className="form-control"
                id="phoneNumber"
                name="phoneNumber"
                value={credentials.phoneNumber}
                onChange={handleChange}
                required
              />
              </div>

              <div className="form-group">
              <label htmlFor="dob">Date of Birth</label>
              <input
                type="date"
                className="form-control"
                id="dob"
                name="dob"
                value={credentials.dob}
                onChange={handleChange}
                required
              />
              </div>

            <div className="form-group">
              <label htmlFor="label">Email Address</label>
              <input
                type="text"
                className="form-control"
                id="username"
                name="username"
                value={credentials.username}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="label">Password</label>
              <input
                type="password"
                className="form-control"
                id="password"
                name="password"
                value={credentials.password}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="label">Confirm password</label>
              <input
                type="password"
                className="form-control"
                id="confirmPassword"
                name="confirmPassword"
                value={credentials.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <Link to="/" className="btn btn-secondary">
                Cancel
              </Link>
              <button type="submit" className="btn btn-primary">
                Sign up
              </button>
            </div>
          </div>
        </form>
      )}
    </div>
  );
}

export default SignUpForm;
