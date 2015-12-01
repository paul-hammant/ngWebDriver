package com.paulhammant.ngwebdriver;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class OleasterIntroductionTest {{
    describe("A suite", () -> {
        it("contains a spec with an expectation", () -> {
            expect(40 + 2).toEqual(42);
        });
        it("contains a second spec with an bad expectation", () -> {
            expect(2 + 2).toEqual(5);
        });
    });
}}