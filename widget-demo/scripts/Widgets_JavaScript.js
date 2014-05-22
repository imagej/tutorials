// @boolean(label = "boolean") pBoolean
// @byte(label = "byte") pByte
// @char(label = "char") pChar
// @double(label = "double") pDouble
// @float(label = "float") pFloat
// @int(label = "int") pInt
// @long(label = "long") pLong
// @short(label = "short") pShort
// @Boolean(label = "Boolean") oBoolean
// @Byte(label = "Byte") oByte
// @Character(label = "Character") oCharacter
// @Double(label = "Double") oDouble
// @Float(label = "Float") oFloat
// @Integer(label = "Integer") oInteger
// @Long(label = "Long") oLong
// @Short(label = "Short") oShort
// @int(min = 0, max = 1000) boundedInteger
// @double(min = 0.2, max = 1000.7, stepSize = 12.34) boundedDouble
// @BigInteger bigInteger
// @BigDecimal bigDecimal
// @String string
// @File file
// @ColorRGB color
// @OUTPUT String result

var sb = new java.lang.StringBuilder();

sb.append("Widgets JavaScript results:\n");

sb.append("\n");
sb.append("\tboolean = " + pBoolean + "\n");
sb.append("\tbyte = " + pByte + "\n");
sb.append("\tchar = " + "'" + pChar + "' [" + pChar.charCodeAt(0) + "]\n");
sb.append("\tdouble = " + pDouble + "\n");
sb.append("\tfloat = " + pFloat + "\n");
sb.append("\tint = " + pInt + "\n");
sb.append("\tlong = " + pLong + "\n");
sb.append("\tshort = " + pShort + "\n");

sb.append("\n");
sb.append("\tBoolean = " + oBoolean + "\n");
sb.append("\tByte = " + oByte + "\n");
oCharValue = oCharacter == null ? "null" : "" + oCharacter.charCodeAt(0);
sb.append("\tCharacter = " + "'" + oCharacter + "' [" + oCharValue + "]\n");
sb.append("\tDouble = " + oDouble + "\n");
sb.append("\tFloat = " + oFloat + "\n");
sb.append("\tInteger = " + oInteger + "\n");
sb.append("\tLong = " + oLong + "\n");
sb.append("\tShort = " + oShort + "\n");

sb.append("\n");
sb.append("\tbounded integer = " + boundedInteger + "\n");
sb.append("\tbounded double = " + boundedDouble + "\n");

sb.append("\n");
sb.append("\tBigInteger = " + bigInteger + "\n");
sb.append("\tBigDecimal = " + bigDecimal + "\n");
sb.append("\tString = " + string + "\n");
sb.append("\tFile = " + file + "\n");
sb.append("\tcolor = " + color + "\n");

result = sb.toString();
