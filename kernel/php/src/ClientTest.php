<?php
namespace EleMe\OpenApi\Kernel;

use PHPUnit\Framework\TestCase;
final class ClientTest extends TestCase{

    public function testA()
    {
        $a = 'a1111';
        echo $a;
        self::assertEquals($a,'a1111');
    }
}