package com.app.dao;

import com.app.model.Memo;

public interface MemoDAO {

	// Create
	 boolean addMemo(Memo memo);

	// Retrieve
	Memo getMemoById(long id);

	Memo getMemoByProperty(long id);

	// Update
	void updateMemo(String subject, String description);

	// Delete
	Memo deleteMemoById(long id);

	Memo deleteMemo(Memo memo);
}
