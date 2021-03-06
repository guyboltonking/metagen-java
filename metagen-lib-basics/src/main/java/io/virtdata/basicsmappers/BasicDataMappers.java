package io.virtdata.basicsmappers;

import com.google.auto.service.AutoService;
import io.virtdata.api.DataMapperLibrary;
import io.virtdata.core.FunctionalDataMappingLibrary;
import io.virtdata.basicsmappers.from_double.to_float.DoubleToFloat;
import io.virtdata.basicsmappers.from_long.to_bigdecimal.ModuloToBigDecimal;
import io.virtdata.basicsmappers.from_long.to_bigint.ToBigInt;
import io.virtdata.basicsmappers.from_long.to_boolean.ModuloToBoolean;
import io.virtdata.basicsmappers.from_long.to_byte.ModuloToByte;
import io.virtdata.basicsmappers.from_long.to_bytebuffer.HashedToByteBuffer;
import io.virtdata.basicsmappers.from_long.to_collection.HashedLineToStringStringMap;
import io.virtdata.basicsmappers.from_long.to_time_types.ToDate;
import io.virtdata.basicsmappers.from_long.to_double.LongRangeToDouble;
import io.virtdata.basicsmappers.from_long.to_inetaddress.ToInetAddress;
import io.virtdata.basicsmappers.from_long.to_int.Add;
import io.virtdata.basicsmappers.from_long.to_long.DivideToLong;
import io.virtdata.basicsmappers.from_long.to_short.ModuloToShort;
import io.virtdata.basicsmappers.from_long.to_string.HashedFileExtractToString;
import io.virtdata.basicsmappers.from_long.to_time_types.ToEpochTimeUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This is a basic data mapping library that contains a variety of functions to build from.</p>
 *
 * <p>This library simply relies on {@link FunctionalDataMappingLibrary}.</p>
 */
@AutoService(DataMapperLibrary.class)
public class BasicDataMappers extends FunctionalDataMappingLibrary {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataMappers.class);

    public final static String dataDir = "data";

    @Override
    public String getLibraryName() {
        return "basics";
    }

    @Override
    public List<Package> getSearchPackages() {
        return new ArrayList<Package>() {
            {
                add(io.virtdata.basicsmappers.unary_string.FuncTemplate.class.getPackage());
                add(io.virtdata.basicsmappers.from_double.to_double.Add.class.getPackage());
                add(io.virtdata.basicsmappers.nondeterministic.ThreadNumToLong.class.getPackage());
                add(io.virtdata.basicsmappers.from_long.to_uuid.ToHashedUUID.class.getPackage());
                add(DoubleToFloat.class.getPackage());
                add(ModuloToBigDecimal.class.getPackage());
                add(ModuloToBoolean.class.getPackage());
                add(ToInetAddress.class.getPackage());
                add(ToBigInt.class.getPackage());
                add(ToDate.class.getPackage());
                add(io.virtdata.basicsmappers.unary_string.Suffix.class.getPackage());
                add(io.virtdata.basicsmappers.from_long.to_long.Add.class.getPackage());
                add(Add.class.getPackage());
                add(io.virtdata.basicsmappers.unary_int.Add.class.getPackage());
                add(DivideToLong.class.getPackage());
                add(ToEpochTimeUUID.class.getPackage());
                add(HashedFileExtractToString.class.getPackage());
                add(HashedLineToStringStringMap.class.getPackage());
                add(HashedToByteBuffer.class.getPackage());
                add(ModuloToShort.class.getPackage());
                add(ModuloToByte.class.getPackage());
                add(LongRangeToDouble.class.getPackage());

            }
        };

    }
}
