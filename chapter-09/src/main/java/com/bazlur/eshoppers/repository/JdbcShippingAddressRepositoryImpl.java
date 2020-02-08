package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.ShippingAddress;
import com.bazlur.eshoppers.jdbc.JDBCTemplate;

import java.util.Optional;

public class JdbcShippingAddressRepositoryImpl implements ShippingAddressRepository {
	private JDBCTemplate jdbcTemplate = new JDBCTemplate();

	private final static String INSERT_SHIPPING_ADDRESS
					= "INSERT INTO shipping_address ( " +
					" address, " +
					" address2, " +
					" state, " +
					" zip, " +
					" country, " +
					" version, " +
					" mobile_number, " +
					" date_created, " +
					" date_last_updated) " +
					" VALUES (?,?,?,?,?,?,?,?,?)";

	public static final String FIND_SHIPPING_ADDRESS_BY_ID
					= "SELECT id, " +
					" address, " +
					" address2, " +
					" state, " +
					" zip, " +
					" country, " +
					" version, " +
					" date_created, " +
					" date_last_updated " +
					" FROM shipping_address WHERE id = ? ";

	@Override
	public ShippingAddress save(ShippingAddress shippingAddress) {
		var id = jdbcTemplate.executeInsertQuery(INSERT_SHIPPING_ADDRESS,
						shippingAddress.getAddress(),
						shippingAddress.getAddress2(),
						shippingAddress.getState(),
						shippingAddress.getZip(),
						shippingAddress.getCountry(),
						0L,
						shippingAddress.getMobileNumber(),
						shippingAddress.getDateCreated(),
						shippingAddress.getDateLastUpdated());

		shippingAddress.setId(id);

		return shippingAddress;
	}

	@Override
	public Optional<ShippingAddress> findOne(long shippingAddressId) {

		var shippingAddresses = jdbcTemplate.queryForObject(
						FIND_SHIPPING_ADDRESS_BY_ID,
						shippingAddressId, resultSet -> {
							var shippingAddress = new ShippingAddress();
							shippingAddress.setId(
											resultSet.getLong("id"));
							shippingAddress.setAddress(
											resultSet.getString("address"));
							shippingAddress.setAddress2(
											resultSet.getString("address2"));
							shippingAddress.setState(
											resultSet.getString("state"));
							shippingAddress.setZip(
											resultSet.getString("zip"));
							shippingAddress.setCountry(
											resultSet.getString("country"));
							shippingAddress.setVersion(
											resultSet.getLong("version"));
							shippingAddress.setMobileNumber(
											resultSet.getString("mobile_number"));
							shippingAddress.setDateCreated(
											resultSet.getTimestamp("date_created")
															.toLocalDateTime());
							shippingAddress.setDateLastUpdated(resultSet
											.getTimestamp("date_last_updated")
											.toLocalDateTime());

							return shippingAddress;
						});

		return shippingAddresses.size() > 0
						? Optional.of(shippingAddresses.get(0))
						: Optional.empty();
	}
}
