const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const fetchProductList = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/getProductList`);
    console.log('API Response:', response);
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    
    return await response.json();
  } catch (error) {
    console.error('Error fetching products:', error);
    throw error;
  }
};
