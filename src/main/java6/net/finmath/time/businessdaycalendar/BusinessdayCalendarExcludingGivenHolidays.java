package net.finmath.time.businessdaycalendar;

import java.util.Set;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

/**
 * An abstract base class for a business day calendar, where every day is a business day, except
 * weekends days provided by a <code>Set</code> provided by the method <code>getHolidays</code>.
 *
 * @author Christian Fries
 */
public abstract class BusinessdayCalendarExcludingGivenHolidays extends BusinessdayCalendar {

	private static final long serialVersionUID = -7060298609732249650L;

	private final String name;
	private final BusinessdayCalendarInterface baseCalendar;
	private final boolean isExcludeWeekends;

	public BusinessdayCalendarExcludingGivenHolidays(String name, BusinessdayCalendarInterface baseCalendar, boolean isExcludeWeekends) {
		super();
		this.name = name;
		this.baseCalendar = baseCalendar;
		this.isExcludeWeekends = isExcludeWeekends;
	}

	/**
	 * @return A name of the calendar (e.g. to identify the calendar).
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return A base calendar, if any. May be null.
	 */
	public BusinessdayCalendarInterface getBaseCalendar() {
		return baseCalendar;
	}

	/**
	 * @return If true, DayOfWeek.SATURDAY and  DayOfWeek.SUNDAY are excluded (no-business days)
	 */
	public boolean isExcludingWeekends() { return isExcludeWeekends; }

	/**
	 * @return A set of (additional) holidays.
	 */
	public abstract Set<LocalDate> getHolidays();

	@Override
	public boolean isBusinessday(LocalDate date) {
		return	(baseCalendar == null || baseCalendar.isBusinessday(date))
				&& (isExcludingWeekends()
						&& date.getDayOfWeek() != DayOfWeek.SATURDAY
						&& date.getDayOfWeek() != DayOfWeek.SUNDAY
						)
				&& !getHolidays().contains(date);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [baseCalendar=" + getBaseCalendar() + "]";
	}
}
