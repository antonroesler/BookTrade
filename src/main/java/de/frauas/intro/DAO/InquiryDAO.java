package de.frauas.intro.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.frauas.intro.model.Inquiry;
import de.frauas.intro.model.User;

@Service
public class InquiryDAO implements Dao<Inquiry> {

	ArrayList<Inquiry> inquiries = new ArrayList<>();

	@Override
	public Optional<Inquiry> get(long id) {
		for (Inquiry inquiry : inquiries) {
			if (inquiry.getId() == id)
				return Optional.ofNullable(inquiry);
		}
		return null;
	}

	@Override
	public List<Inquiry> getAll() {
		return inquiries;
	}

	@Override
	public void save(Inquiry t) {
		inquiries.add(t);

	}

	@Override
	public void update(Inquiry t, String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Inquiry t) {
		inquiries.remove(t);

	}

	public List<Inquiry> getAllInquiriesRequestedToUser(String userHash) {
		ArrayList<Inquiry> userInquiries = new ArrayList<>();
		for (Inquiry inquiry : inquiries) {
			if (inquiry.getRequestedUser().getHash() == userHash) {
				userInquiries.add(inquiry);
			}
		}
		return userInquiries;
	}

}
