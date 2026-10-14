package com.paubox.data;

/**
 * Pagination metadata for a page of endpoints.
 *
 * <p>Distinct from {@link PageInfo}, which the Forms API returns: that one also
 * carries {@code page} and {@code pages}, and reusing it here would leave those
 * sitting at zero as if the service had reported them.</p>
 */
public class WebhookPageInfo {

	/** The total number of endpoints matching the request, not the page size. */
	private long count;

	/** How many rows are on this page. */
	private int items;

	public long getCount() { return count; }
	public void setCount(long count) { this.count = count; }

	public int getItems() { return items; }
	public void setItems(int items) { this.items = items; }
}
