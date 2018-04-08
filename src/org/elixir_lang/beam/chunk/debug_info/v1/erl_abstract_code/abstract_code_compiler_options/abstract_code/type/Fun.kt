package org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.abstract_code.type

import com.ericsson.otp.erlang.OtpErlangList
import com.ericsson.otp.erlang.OtpErlangObject
import com.ericsson.otp.erlang.OtpErlangTuple
import org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.abstract_code.MacroString
import org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.abstract_code.Type.ifSubtypeTo
import org.elixir_lang.beam.chunk.debug_info.v1.erl_abstract_code.abstract_code_compiler_options.abstract_code.type.`fun`.ParameterReturn

object Fun {
    fun ifToMacroString(type: OtpErlangTuple): MacroString? = ifTo(type) { toMacroString(type) }

    fun ifToMacroString(type: OtpErlangTuple, nameMacroString: MacroString): MacroString? =
            ifTo(type) { toMacroString(type, nameMacroString) }

    private const val SUBTYPE = "fun"

    private fun <T> ifTo(type: OtpErlangTuple, ifTrue: (OtpErlangTuple) -> T): T? = ifSubtypeTo(type, SUBTYPE, ifTrue)

    private fun toMacroString(type: OtpErlangTuple): MacroString {
        TODO()
    }

    private fun toMacroString(type: OtpErlangTuple, nameMacroString: MacroString): MacroString {
        val parameterReturn = Fun.toParameterReturn(type)

        return when (parameterReturn) {
            is OtpErlangList -> ParameterReturn.toMacroString(parameterReturn, nameMacroString)
            else -> "unknown_parameter_return"
        }
    }

    private fun toParameterReturn(type: OtpErlangTuple): OtpErlangObject? = type.elementAt(3)
}
