import { useState } from 'react';
import './App.css';

function App() {
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);
    setPreview(file ? URL.createObjectURL(file) : null);
    setResult(null);
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!image) {
      setError('Please select an image.');
      return;
    }
    setLoading(true);
    setError('');
    setResult(null);
    try {
      const formData = new FormData();
      formData.append('image', image);
      const response = await fetch('http://localhost:8080/identify', {
        method: 'POST',
        body: formData,
      });
      if (!response.ok) throw new Error('Failed to get result');
      const data = await response.json();
      setResult(data);
    } catch (err) {
      setError('Error: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Blood Type Identifier</h1>
      <form onSubmit={handleSubmit} className="upload-form">
        <input type="file" accept="image/*" onChange={handleImageChange} />
        {preview && (
          <div className="preview">
            <img src={preview} alt="Preview" height={150} />
          </div>
        )}
        <button type="submit" disabled={loading}>
          {loading ? 'Identifying...' : 'Identify Blood Type'}
        </button>
      </form>
      {error && <div className="error">{error}</div>}
      {result && (
        <div className="result">
          <h2>Predicted Blood Type: {result.bloodType}</h2>
          {result.imagePath && (
            <img src={result.imagePath} alt="Result" height={150} />
          )}
        </div>
      )}
    </div>
  );
}

export default App;
