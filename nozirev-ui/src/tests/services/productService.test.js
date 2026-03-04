import { fetchProductList } from '../../services/productService';

describe('productService - fetchProductList', () => {
  beforeEach(() => {
    global.fetch = jest.fn();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  test('calls the correct API endpoint', async () => {
    global.fetch.mockResolvedValue({ ok: true, json: async () => [] });
    await fetchProductList('smartphones');
    expect(global.fetch).toHaveBeenCalledWith(
      expect.stringContaining('/api/getProductList'),
      expect.any(Object)
    );
  });

  test('uses POST method', async () => {
    global.fetch.mockResolvedValue({ ok: true, json: async () => [] });
    await fetchProductList('smartphones');
    const options = global.fetch.mock.calls[0][1];
    expect(options.method).toBe('POST');
  });

  test('sends JSON Content-Type header', async () => {
    global.fetch.mockResolvedValue({ ok: true, json: async () => [] });
    await fetchProductList('smartphones');
    const options = global.fetch.mock.calls[0][1];
    expect(options.headers['Content-Type']).toBe('application/json');
  });

  test('sends correct category in request body', async () => {
    global.fetch.mockResolvedValue({ ok: true, json: async () => [] });
    await fetchProductList('tablets');
    const options = global.fetch.mock.calls[0][1];
    expect(JSON.parse(options.body)).toEqual({ type: 'tablets' });
  });

  test('returns parsed JSON on success', async () => {
    const mockData = [{ id: 1, productName: 'iPhone 11' }];
    global.fetch.mockResolvedValue({ ok: true, json: async () => mockData });
    const result = await fetchProductList('smartphones');
    expect(result).toEqual(mockData);
  });

  test('throws error when response is not ok', async () => {
    global.fetch.mockResolvedValue({ ok: false });
    await expect(fetchProductList('smartphones')).rejects.toThrow('Failed to fetch products');
  });

  test('throws error when fetch itself fails', async () => {
    global.fetch.mockRejectedValue(new Error('Network error'));
    await expect(fetchProductList('smartphones')).rejects.toThrow('Network error');
  });

  test('defaults to smartphones when no category given', async () => {
    global.fetch.mockResolvedValue({ ok: true, json: async () => [] });
    await fetchProductList();
    const options = global.fetch.mock.calls[0][1];
    expect(JSON.parse(options.body)).toEqual({ type: 'smartphones' });
  });
});
