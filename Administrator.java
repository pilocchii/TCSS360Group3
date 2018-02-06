package classes;

/**
 * 
 * @author Abderisaq Tarabi
 *
 */
public class Administrator extends User {

	public Administrator(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}
	
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean equals(final Object theOther) {
//        
//        final boolean result;
//        
//        if (this == theOther) {
//            result = true;
//        } else if (theOther == null || getClass() != theOther.getClass()) {
//            result = false;
//        } else {
//            final Manager otherManger = (Manager) theOther;
//            result = getFirstName().equals(otherManger.getFirstName()) 
//                    && getLastName().equals(otherManger.getLastName())
//                    && getEmail().equals(otherManger.getEmail())
//                    && getPhoneNumber().equals(otherManger.getPhoneNumber());
//        }
//        
//        return result;
//    }

}
