require 'chunky_png'

images = [
  ChunkyPNG::Image.from_file(ARGV[0]),
  ChunkyPNG::Image.from_file(ARGV[1])
]

diff = []

images.first.height.times do |y|
  images.first.row(y).each_with_index do |pixel, x|
    diff << [x,y] unless pixel == images.last[x,y]
  end
end

puts "pixels (total):     #{images.first.pixels.length}"
puts "pixels changed:     #{diff.length}"
puts "pixels changed (%): #{(diff.length.to_f / images.first.pixels.length) * 100}%"
puts "#{diff.first}"

xDiffPixels = []
yDiffPixels = []
diff.each do |x|
    xDiffPixels << x[0]
    yDiffPixels << x[1]
end

puts "xDiffPixels : #{xDiffPixels.length}"
puts "yDiffPixels : #{yDiffPixels.length}"

unless diff.length.zero?
    xDiffPixels.length.times do |x|
        images.last.compose_pixel(xDiffPixels[x], yDiffPixels[x], ChunkyPNG::Color.rgb(0,255,127))
    end
    images.last.save(ARGV[2])
end


#if "#{diff.length}"!="0"
#  xDiffPixels.length.times do |x|
#    yDiffPixels.length.times do |y|
#      images.last.compose_pixel(xDiffPixels[x], yDiffPixels[x], ChunkyPNG::Color.rgb(0,255,127))
#    end
#  end
#  images.last.save(ARGV[2])
#end
