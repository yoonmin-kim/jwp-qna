package qna.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DeleteHistories {

	private final List<DeleteHistory> deleteHistories;

	public DeleteHistories(List<DeleteHistory> deleteHistories) {
		this.deleteHistories = deleteHistories;
	}

	public DeleteHistories(DeleteHistory... deleteHistories) {
		this.deleteHistories = Arrays.asList(deleteHistories);
	}
  
	public List<DeleteHistory> getDeleteHistories() {
		return deleteHistories;
	}

	public boolean contains(DeleteHistory deleteHistory) {
		return deleteHistories.contains(deleteHistory);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DeleteHistories that = (DeleteHistories)o;
		return Objects.equals(deleteHistories, that.deleteHistories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deleteHistories);
	}
}